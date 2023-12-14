import cs2030s.fp.Combiner;
import cs2030s.fp.Immutator;
import java.util.Scanner;

class Lab7 {
  public static void main(String[] args) {
    // Create a scanner to read from standard input.
    Scanner sc = new Scanner(System.in);

    // Read a single integer from the test file
    // and then run the appropriate test case
    switch (sc.nextInt()) {
      case 1:
        test1();
        break;
      case 2:
        test2();
        break;
      case 3:
        test3();
        break;
      case 4:
        test4();
        break;
      case 5:
        test5();
        break;
      default:
        break;
    }
  }
  
  public static void test1() {
    int[] eval = new int[]{0};
    Immutator<Integer, Integer> incr = x -> {
      eval[0] += 1;
      return x + 1;
    };
    
    MemoList<Integer> l = MemoList.generate(4, 0, incr);
    
    System.out.println(l.toString());
    System.out.println(eval[0]);
    System.out.println(l.indexOf(2));
    System.out.println(eval[0]);
    System.out.println(l.indexOf(1));
    System.out.println(eval[0]);
    System.out.println(l.get(1));
    System.out.println(eval[0]);
    System.out.println(l.get(3));
    System.out.println(eval[0]);
  }
  
  public static void test2() {
    int[] eval = new int[]{0};
    Combiner<Integer, Integer, Integer> fib = (x, y) -> {
      eval[0] += 1;
      return x + y;
    };
    
    MemoList<Integer> l = MemoList.generate(8, 0, 1, fib);
    
    System.out.println(l.toString());
    System.out.println(eval[0]);
    System.out.println(l.indexOf(5));
    System.out.println(eval[0]);
    System.out.println(l.indexOf(3));
    System.out.println(eval[0]);
    System.out.println(l.get(4));
    System.out.println(eval[0]);
    System.out.println(l.get(6));
    System.out.println(eval[0]);
  }
  
  public static void test3() {
    int[] incrArr = new int[]{0};
    Immutator<Integer, Integer> incr = x -> {
      incrArr[0] += 1;
      return x + 1;
    };
    int[] dblArr = new int[]{0};
    Immutator<Integer, Integer> dbl = x -> {
      dblArr[0] += 1;
      return x + x;
    };
    
    MemoList<Integer> nat = MemoList.generate(10, 0, incr);
    
    System.out.println(nat.toString());
    System.out.println(incrArr[0]);
    System.out.println(dblArr[0]);
    
    MemoList<Integer> even = nat.map(dbl);
    
    System.out.println(even.toString());
    System.out.println(nat.indexOf(2));
    System.out.println(incrArr[0]);
    System.out.println(dblArr[0]);
    System.out.println(even.indexOf(6));
    System.out.println(incrArr[0]);
    System.out.println(dblArr[0]);
    
    MemoList<Integer> odd = even.map(incr);
    
    System.out.println(odd.toString());
    System.out.println(incrArr[0]);
    System.out.println(dblArr[0]);
    System.out.println(odd.get(8));
    System.out.println(incrArr[0]);
    System.out.println(dblArr[0]);
  }
  
  public static void test4() {
    int[] duplArr = new int[]{0};
    int[] copyArr = new int[]{0};
    Immutator<MemoList<Integer>, Integer> dupl = x -> {
      duplArr[0] += 1;
      return MemoList.generate(x, x, n -> {
        copyArr[0] += 1;
        return x;
      });
    };
    
    MemoList<Integer> nat = MemoList.generate(5, 1, x -> x + 1);
    
    System.out.println(nat.toString());
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    
    MemoList<Integer> superNat = nat.flatMap(dupl);
    System.out.println(superNat.toString());
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    
    int[] dblArr = new int[]{0};
    Immutator<Integer, Integer> dbl = x -> {
      dblArr[0] += 1;
      return x * 2;
    };
    
    MemoList<Integer> superEven = superNat.map(dbl);
    
    System.out.println(superEven.toString());
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    System.out.println(dblArr[0]);
    System.out.println(superEven.get(12));
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    System.out.println(dblArr[0]);
    System.out.println(superEven.toString());
    System.out.println(superNat.toString());
    System.out.println(nat.toString());
    
    MemoList<Integer> nat2 = MemoList.generate(5, 1, x -> x + 1);
    MemoList<MemoList<Integer>> nestNat2 = nat2.map(dupl);
    
    System.out.println(nestNat2.toString());
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    System.out.println(dblArr[0]);
    System.out.println(nestNat2.get(2));
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    System.out.println(dblArr[0]);
    
    for (int i = 0; i < 5; i++) {
      nestNat2.get(i);
    }
    System.out.println(nestNat2.toString());
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    System.out.println(dblArr[0]);
    
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j <= i; j++) {
        nestNat2.get(i).get(j);
      }
    }
    System.out.println(nestNat2.toString());
    System.out.println(duplArr[0]);
    System.out.println(copyArr[0]);
    System.out.println(dblArr[0]);
  }
  
  public static void test5() {

    class A {

      private int x;

      public A(int x) {
        this.x = x;
      }

      public int getX() {
        return this.x;
      }

      @Override
      public String toString() {
        return "A:" + this.x;
      }

    }

    class B extends A {

      public B(int x) {
        super(x);
      }

      @Override
      public String toString() {
        return "B:" + super.toString();
      }

    }

    class C extends B {

      public C(int x) {
        super(x);
      }

      @Override
      public String toString() {
        return "C:" + super.toString();
      }

    }
    
    Immutator<C, A> incr = x -> new C(x.getX() + 1);

    Combiner<C, A, A> fib = (x, y) -> new C(x.getX() + y.getX());

    Immutator<MemoList<B>, A> dbl  = x -> MemoList.generate(
        x.getX(), new C(x.getX()), y -> new C(y.getX() + 1));

    MemoList<B> blist1 = MemoList.generate(8, new C(0), incr);
    
    System.out.println(blist1.toString());
    
    MemoList<B> blist2 = MemoList.generate(8, new C(0), new C(1), fib);
    
    System.out.println(blist2.toString());
    
    MemoList<B> blist3 = blist1.map(incr);
    
    System.out.println(blist3.toString());
    
    MemoList<B> blist4 = blist1.flatMap(dbl);
    
    System.out.println(blist4.toString());
  }

}
