import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Set;

public class TestLocalDateTime {
    //1.LocalDate LocalTime LocalDateTime
    @Test
    public void test1(){
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        LocalDateTime of = LocalDateTime.of(2015, 10, 19, 12, 22, 33);
        System.out.println(of);

        System.out.println(localDateTime.plusWeeks(3));
        System.out.println(localDateTime.minusDays(2).plusWeeks(3));
    }

    //2. Instant:时间戳（以Unix元年：1970年1月1日00:00:00到某个时间之间的毫秒值）
    @Test
    public void test2(){
        Instant instant = Instant.now();//默认获取UTC时区
        System.out.println(instant);

        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println(offsetDateTime);
        System.out.println(instant.toEpochMilli());

        Instant instant1 = Instant.ofEpochSecond(60);
        System.out.println(instant1);
    }

    //3.Duration:计算两个时间之间的间隔
    //Period:计算两个日期之间的间隔
    @Test
    public void test3() throws InterruptedException {
        Instant ins1 = Instant.now();
        Thread.sleep(1000);
        Instant ins2 = Instant.now();
        Duration between = Duration.between(ins1, ins2);
        System.out.println(between.getSeconds());
        System.out.println(between.toMillis());
        System.out.println(between.toHours());
        System.out.println("------------");
        LocalTime lt1 = LocalTime.now();
        Thread.sleep(1000);
        LocalTime lt2 = LocalTime.now();
        System.out.println(Duration.between(lt1,lt2).toMillis());
        System.out.println("------------");
        LocalDate localDate = LocalDate.of(2014, 1, 1);
        LocalDate localDate1 = LocalDate.now();
        Period between1 = Period.between(localDate, localDate1);
        System.out.println(between1.toTotalMonths());
    }

    // TemporalAdjuster:时间矫正器
    @Test
    public void test4(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime with = ldt.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime with2 = ldt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println(with);
        System.out.println(with2);

        //自定义：下一个工作日
        LocalDateTime with1 = ldt.with(l -> {
            LocalDateTime ldt1 = (LocalDateTime) l;
            DayOfWeek dayOfWeek = ldt1.getDayOfWeek();
            if (dayOfWeek.equals(DayOfWeek.FRIDAY)) {
                return ldt1.plusDays(3);
            } else if (dayOfWeek.equals(DayOfWeek.SATURDAY)) {
                return ldt1.plusDays(2);
            } else {
                return ldt1.plusDays(1);
            }
        });
        System.out.println(with1);
    }
    //DateTimeFormatter:格式化时间/日期
    @Test
    public void test5() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE;
        LocalDateTime l = LocalDateTime.now();
        String strDate = l.format(dateTimeFormatter);
        System.out.println(strDate);
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        System.out.println(l.format(dateTimeFormatter1));
        LocalDateTime of = LocalDateTime.parse(l.format(dateTimeFormatter1), dateTimeFormatter1);
        System.out.println(of);
    }

    //ZonedDate\ZonedTime\ZonedDateTime
    @Test
    public void test7(){
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        availableZoneIds.forEach(System.out::println);
    }

    @Test
    public void test8() {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(localDateTime);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zonedDateTime);
    }
}
