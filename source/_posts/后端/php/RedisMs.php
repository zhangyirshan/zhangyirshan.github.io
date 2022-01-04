<?php

/**
 * Class RedisMs：redis主从复制
 */
class RedisMs {
    /**
     * 对象实例数组
     * @var array
     */
    private static $_instance = [];

    /**
     * hander
     * @var null|redis
     */
    protected $handler = null;

    /**
     * 配置
     * @var array
     */
    protected static $config = [
        'host' => '127.0.0.1,127.0.0.1,127.0.0.1', // redis主机
        'port' => '6379,6379,6379', // redis端口
        'password' => ',,', // 密码
        'select' => '0,0,0', // 操作库
    ];

    /**
     * debug模式
     * @var bool
     */
    public $debug = false;

    /*
     * 单例模式
     */
    private function __construct($host, $port, $auth, $select, $debug = false) {
        if (!$this->handler) {
            $this->handler = new Redis();
        }

        $this->handler->connect($host, $port, 1000, NULL, 100);

        if ('' != $auth) {
            $this->handler->auth($auth);
        }

        if (0 != $select) {
            $this->handler->select($select);
        }

        $this->debug = $debug;
    }

    public static function getInstance($debug = false) {
        // 检测php环境
        if (!extension_loaded('redis')) {
            throw new Exception('not support:redis');
        }

        $config = self::$config;

        $hosts = explode(",", $config['host']);
        $ports = explode(",", $config['port']);
        $pwds = explode(",", $config['password']);
        $selects = explode(",", $config['select']);

        if (count($hosts) < 2 || $ports < 2) {
            throw new Exception('config error： at least 2 host and 2 port needed');
        }

        foreach ($hosts as $key => $host) {
            $port = !empty($ports[$key]) ? $ports[$key] : 6379;
            $pwd = !empty($pwds[$key]) ? $pwds[$key] : '';
            $select = intval($selects[$key]);

            if (!isset(self::$_instance[$key]) || !self::$_instance[$key] instanceof self) {
                self::$_instance[$key] = new self($host, $port, $pwd, $select, $debug);
            }
        }

        return self::$_instance[0];
    }

    /**
     * 获取主服务器
     * @return mixed
     */
    public function master() {
        if ($this->debug) {
            echo 'i am master <br />';
        }

        return self::$_instance[0];
    }

    /**
     * 获取从服务器
     */
    public function slaves() {
        $slaves = [];
        for ($i = 1; $i < count(self::$_instance); $i++) {
            $slaves[] = self::$_instance[$i];
        }

        return $slaves;
    }

    /**
     * 随机生成一台从服务器
     */
    public function oneSlave() {
        $slaves = $this->slaves();
        $count = count($slaves);
        $i = mt_rand(0, $count - 1);

        if ($this->debug) {
            echo 'i am slave ' . $i . '<br />';
        }

        return self::$_instance[$i];
    }

    /**
     * 执行命令
     */
    public function runCommand($command, $params) {
        try {
            $redis = $this->getByCommand($command);

            $result = call_user_func_array([$redis, $command], $params);
            $this->changeMasterDb();
            $redis = $this->master()->handler;
            return call_user_func_array([$redis, $command], $params);
        } catch (Exception $e) {
            throw new Exception($e->getMessage(), $e->getCode());
        }

    }

    /**
     * 切换主从数据库
     */
    private function changeMasterDb() {
        try {
            for($i=1; $i < count(self::$_instance);$i++){
                $result = self::$_instance[$i]->handler->info();
                echo($result['role']);
                if($result){
                    $temp = self::$_instance[0];
                    self::$_instance[0] = self::$_instance[$i];
                    self::$_instance[$i] = $temp;
                }
            }
        } catch (Exception $e) {
            throw new Exception($e->getMessage(), $e->getCode());
        }
    }

    /**
     * 根据command命令来获取服务器
     */
    protected function getByCommand($command) {
        //TODO::这里需要完善，只列出了部分读命令
        $read_command = ['get', 'hGet', 'lRange'];
        $write_command = ['set', 'hSet','expire','incr'];

        if (in_array($command, $read_command)) {     //读命令，随机返回一台读服务器
            return $this->oneSlave()->handler;
        } elseif (in_array($command, $write_command)) {
            return $this->master()->handler;
        } else {
            throw new Exception('不支持该命令：' . $command);
        }
    }

    private function __clone() {

    }

}
