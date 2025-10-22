import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class TestTransaction {
	
	List<Transaction> transactions = null;
	
	@Before
	public void before(){
		Trader raoul = new Trader("Raoul", "Cambridge");
		Trader mario = new Trader("Mario", "Milan");
		Trader alan = new Trader("Alan", "Cambridge");
		Trader brian = new Trader("Brian", "Cambridge");
		
		transactions = Arrays.asList(
				new Transaction(brian, 2011, 300),
				new Transaction(raoul, 2012, 1000),
				new Transaction(raoul, 2011, 400),
				new Transaction(mario, 2012, 710),
				new Transaction(mario, 2012, 700),
				new Transaction(alan, 2012, 950)
		);
	}
	
	//1. 找出2011年发生的所有交易， 并按交易额排序（从低到高）
	@Test
	public void test1(){
		transactions.stream().filter(transaction -> transaction.getYear() == 2011)
				.sorted(Comparator.comparingInt(Transaction::getValue))
//				.sorted((v1,v2)->v2.getValue() - v1.getValue())   从高到低
				.forEach(System.out::println);
	}
	
	//2. 交易员都在哪些不同的城市工作过？
	@Test
	public void test2(){
		transactions.stream().map(transaction -> transaction.getTrader().getCity())
				.distinct().forEach(System.out::println);
	}
	
	//3. 查找所有来自剑桥的交易员，并按姓名排序
	@Test
	public void test3(){
		transactions.stream().map(Transaction::getTrader).filter(trader -> trader.getCity().equals("Cambridge"))
				.sorted(Comparator.comparing(Trader::getName)).distinct()
				.forEach(System.out::println);
	}
	
	//4. 返回所有交易员的姓名字符串，按字母顺序排序
	@Test
	public void test4(){
		transactions.stream().map(transaction -> transaction.getTrader().getName())
				.sorted().forEach(System.out::println);
		System.out.println("----------------");
		String collect = transactions.stream().map(transaction -> transaction.getTrader().getName())
				.sorted().distinct().collect(Collectors.joining(",", "[", "]"));
		System.out.println(collect);
		System.out.println("----------------");
		transactions.stream().map(transaction -> transaction.getTrader().getName()).flatMap(TestTransaction::filterCharacter)
				.sorted(String::compareToIgnoreCase).forEach(System.out::print);
	}
	
	public static Stream<String> filterCharacter(String str){
		List<String> list = new ArrayList<>();
		
		for (Character ch : str.toCharArray()) {
			list.add(ch.toString());
		}
		
		return list.stream();
	}
	
	//5. 有没有交易员是在米兰工作的？
	@Test
	public void test5(){
		boolean milan = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
		System.out.println(milan);
	}
	
	
	//6. 打印生活在剑桥的交易员的所有交易额
	@Test
	public void test6(){
		Integer cambridge = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
				.map(Transaction::getValue).reduce(0, Integer::sum);
		Integer cambridge1 = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
				.collect(Collectors.summingInt(Transaction::getValue));
		Integer cambridge2 = transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
				.mapToInt(Transaction::getValue).sum();

		System.out.println(cambridge);
		System.out.println(cambridge1);
		System.out.println(cambridge2);
	}
	
	
	//7. 所有交易中，最高的交易额是多少
	@Test
	public void test7(){
		Optional<Integer> max = transactions.stream().map(Transaction::getValue).max(Integer::compare);
		System.out.println(max.orElse(0));
	}
	
	//8. 找到交易额最小的交易
	@Test
	public void test8(){
		Optional<Transaction> min = transactions.stream().min(Comparator.comparingInt(Transaction::getValue));
		System.out.println(min.get());
	}
}