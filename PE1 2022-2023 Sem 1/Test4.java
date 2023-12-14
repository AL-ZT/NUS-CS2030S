class Test4 {
  public static void main(String[] args) {
    CS2030STest we = new CS2030STest();
    
    Queueable<Integer> qInt = new Sequeue<>(1, new Sequeue<>(2, new Sequeue<>(3, new EndQueue<>())));
    we.expect("Queueable<Integer> qInt = new Sequeue<>(1, new Sequeue<>(2, new Sequeue<>(3, new EndQueue<>())))", qInt.toString(), "1; 2; 3; {END}");
    
    Queueable<String> qStr = new Sequeue<>("A", new Sequeue<>("B", new Sequeue<>("C", new EndQueue<>())));
    we.expect("Queueable<String> qStr = new Sequeue<>(\"A\", new Sequeue<>(\"B\", new Sequeue<>(\"C\", new EndQueue<>())))", qStr.toString(), "A; B; C; {END}");
    
    Queueable<?> zQue = Queues.zip(qInt, qStr);
    we.expect("Queueable<?> zQue = Queues.zip(qInt, qStr)", zQue.toString(), "1; A; 2; B; 3; C; {END}");
    we.expect("zQue.peek()", zQue.peek().toString(), "1; A");
    we.expect("zQue.deq().peek()", zQue.deq().peek().toString(), "2; B");
    
    Queueable<?> mQue = Queues.merge(qInt, qStr);
    we.expect("Queueable<?> mQue = Queues.merge(qInt, qStr)", zQue.toString(), "1; A; 2; B; 3; C; {END}");
    we.expect("mQue.peek()", mQue.peek().toString(), "1");
    we.expect("mQue.deq().peek()", mQue.deq().peek().toString(), "A");
    
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
    
    Queueable<B> qB1 = new Sequeue<>(new C(), new Sequeue<>(new C(), new Sequeue<>(new C(), new EndQueue<>())));
    we.expect("Queueable<B> qB1 = new Sequeue<>(new C(), new Sequeue<>(new C(), new Sequeue<>(new C(), new EndQueue<>())))", qB1.toString(), "{C}; {C}; {C}; {END}");
    Queueable<B> qB2 = new Sequeue<>(new C(), new Sequeue<>(new C(), new Sequeue<>(new C(), new EndQueue<>())));
    we.expect("Queueable<B> qB2 = new Sequeue<>(new C(), new Sequeue<>(new C(), new Sequeue<>(new C(), new EndQueue<>())))", qB2.toString(), "{C}; {C}; {C}; {END}");
    
    we.expect("Queues.<A>zip(qB1, qB2)", Queues.<A>zip(qB1, qB2).toString(), "{C}; {C}; {C}; {C}; {C}; {C}; {END}");
    we.expect("Queues.<A>zip(qB1, qB2).peek()", Queues.<A>zip(qB1, qB2).peek().toString(), "{C}; {C}");
    we.expect("Queues.<A>merge(qB1, qB2)", Queues.<A>merge(qB1, qB2).toString(), "{C}; {C}; {C}; {C}; {C}; {C}; {END}");
    we.expect("Queues.<A>merge(qB1, qB2).peek()", Queues.<A>merge(qB1, qB2).peek().toString(), "{C}");
  }
}