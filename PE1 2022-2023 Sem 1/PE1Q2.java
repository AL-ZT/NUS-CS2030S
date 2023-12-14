import java.util.Scanner;
    
class A {
  @Override
  public String toString() {
    return "{A}";
  }
}
class B extends A {
  @Override
  public String toString() {
    return "{B}";
  }
}
class C extends B {
  @Override
  public String toString() {
    return "{C}";
  }
}

class PE1Q2 {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    switch(sc.nextInt()) {
      case 1:
        test1();
        break;
      case 2:
        test2();
        break;
    }
  }
  
  public static void test1() {
    System.out.println("--- Test #1 ---");
    Queueable<Integer> qInt = new EndQueue<>();
    System.out.println(qInt.toString());
    qInt = qInt.enq(1);
    System.out.println(qInt.toString());
    qInt = qInt.enq(2);
    System.out.println(qInt.toString());
    qInt = qInt.enq(1);
    System.out.println(qInt.toString());
    System.out.println("qInt.size(): " + qInt.size());
    System.out.println("qInt.peek(): " + qInt.peek().toString());
    qInt.deq();
    System.out.println(qInt.toString());
    System.out.println("qInt.size(): " + qInt.size());
    System.out.println("qInt.peek(): " + qInt.peek().toString());
    qInt = qInt.deq();
    System.out.println(qInt.toString());
    System.out.println("qInt.size(): " + qInt.size());
    System.out.println("qInt.peek(): " + qInt.peek().toString());
    
    Queueable<String> qStr = new EndQueue<>();
    System.out.println(qStr.toString());
    qStr = qStr.enq("A");
    System.out.println(qStr.toString());
    qStr = qStr.enq("B");
    System.out.println(qStr.toString());
    qStr = qStr.enq("A");
    System.out.println(qStr.toString());
    System.out.println("qStr.size(): " + qStr.size());
    System.out.println("qStr.peek(): " + qStr.peek().toString());
    qStr.deq();
    System.out.println(qStr.toString());
    System.out.println("qStr.size(): " + qStr.size());
    System.out.println("qStr.peek(): " + qStr.peek().toString());
    qStr = qStr.deq();
    System.out.println(qStr.toString());
    System.out.println("qStr.size(): " + qStr.size());
    System.out.println("qStr.peek(): " + qStr.peek().toString());
  }
  
  public static void test2() {
    System.out.println("--- Test #2 ---");
    Queueable<Integer> qInt = new Sequeue<>(1, new Sequeue<>(2, new Sequeue<>(3, new EndQueue<>())));
    Queueable<String> qStr = new Sequeue<>("A", new Sequeue<>("B", new Sequeue<>("C", new EndQueue<>())));
    
    System.out.println(qInt.toString());
    System.out.println(qStr.toString());
    
    Queueable<?> zQue = Queues.zip(qInt, qStr);
    Queueable<?> mQue = Queues.merge(qInt, qStr);
    
    System.out.println(zQue.toString());
    System.out.println("peek(): " + zQue.peek().toString());
    System.out.println("deq().peek(): " + zQue.deq().peek().toString());
    
    System.out.println(mQue.toString());
    System.out.println("peek(): " + mQue.peek().toString());
    System.out.println("deq().peek(): " + mQue.deq().peek().toString());
    
    Queueable<B> qB1 = new Sequeue<>(new C(), new Sequeue<>(new C(), new Sequeue<>(new C(), new EndQueue<>())));
    Queueable<B> qB2 = new Sequeue<>(new C(), new Sequeue<>(new C(), new Sequeue<>(new C(), new EndQueue<>())));
    
    System.out.println(qB1.toString());
    System.out.println(qB2.toString());
    
    System.out.println("<A>zip: " + Queues.<A>zip(qB1, qB2).toString());
    System.out.println("<A>zip.peek: " + Queues.<A>zip(qB1, qB2).peek().toString());
    
    System.out.println("<A>merge: " + Queues.<A>merge(qB1, qB2).toString());
    System.out.println("<A>merge.peek: " + Queues.<A>merge(qB1, qB2).peek().toString());
  }
}