class Test3 {
  public static void main(String[] args) {
    CS2030STest we = new CS2030STest();
    
    Queueable<Integer> qInt = new EndQueue<>();
    we.expect("Queueable<Integer> qInt = new EndQueue<>()", qInt.toString(), "{END}");
    
    qInt = qInt.enq(1);
    we.expect("qInt = qInt.enq(1)", qInt.toString(), "1; {END}");
    
    qInt = qInt.enq(2);
    we.expect("qInt = qInt.enq(2)", qInt.toString(), "1; 2; {END}");
    
    qInt = qInt.enq(1);
    we.expect("qInt = qInt.enq(1)", qInt.toString(), "1; 2; 1; {END}");
    we.expect("qInt.size()", qInt.size(), 3);
    we.expect("qInt.peek()", qInt.peek(), 1);
    
    qInt = qInt.deq();
    we.expect("qInt = qInt.deq()", qInt.toString(), "2; 1; {END}");
    we.expect("qInt.size()", qInt.size(), 2);
    we.expect("qInt.peek()", qInt.peek(), 2);
    
    Queueable<String> qStr = new Sequeue<>("A", new EndQueue<>());
    we.expect("Queueable<String> qStr = new Sequeue<>(\"A\", new EndQueue<>())", qStr.toString(), "A; {END}");
    
    qStr = qStr.enq("B");
    we.expect("qStr = qStr.enq(\"B\")", qStr.toString(), "A; B; {END}");
    
    qStr = qStr.enq("A");
    we.expect("qStr = qStr.enq(\"A\")", qStr.toString(), "A; B; A; {END}");
    we.expect("qStr.size()", qStr.size(), 3);
    we.expect("qStr.peek()", qStr.peek(), "A");
    
    qStr = qStr.deq();
    we.expect("qStr = qStr.deq()", qStr.toString(), "B; A; {END}");
    we.expect("qStr.size()", qStr.size(), 2);
    we.expect("qStr.peek()", qStr.peek(), "B");
  }
}