import java.util.Scanner;

class PE1Q1 {
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
    Candy c = new CandyBag(5, 4);
    System.out.println("--- Test #1 ---");
    
    System.out.println(c.toString());
    System.out.println("c.getWeight: " + c.getWeight());
    System.out.println("c.isEaten: " + c.isEaten());
    System.out.println("c.eat()"); c.eat();
    System.out.println(c.toString());
    System.out.println("c.eat()"); c.eat();
    System.out.println("c.eat()"); c.eat();
    System.out.println("c.eat()"); c.eat();
    System.out.println(c.toString());
    System.out.println("c.getWeight: " + c.getWeight());
    System.out.println("c.isEaten: " + c.isEaten());
    System.out.println("c.eat()"); c.eat();
    System.out.println("c.isEaten: " + c.isEaten());
    System.out.println(c.toString());
    System.out.println("c.eat()"); c.eat();
    System.out.println("c.isEaten: " + c.isEaten());
    System.out.println(c.toString());
    
    c = new MysteryCandy("Sour", 3);
    System.out.println(c.toString());
    System.out.println("c.eat()"); c.eat();
    System.out.println("c.isEaten: " + c.isEaten());
    System.out.println(c.toString());
    
    MysteryCandy mc = (MysteryCandy) c;
    System.out.println(mc.toString());
    mc.open();
    System.out.println(c.toString());
    System.out.println("c.eat()"); c.eat();
    System.out.println(c.toString());
    System.out.println("c.isEaten: " + c.isEaten());
  }
  
  public static void test2() {
    Candy c = new MysteryCandy("Choco", 3);
    Treaters adi = new Treaters("Adi", c, 5);
    Trickers ida = new Trickers("Ida", "Spin", 5);
    Trickers ada = new Trickers("Ada", "Jump", 6);
    Trickers idi = new Trickers("Idi", "Boo!", 20);
    
    System.out.println("--- Test #2 ---");
    System.out.println(c.toString());
    System.out.println(adi.toString());
    System.out.println(ida.toString());
    System.out.println(ada.toString());
    System.out.println(idi.toString());
    
    System.out.println("ida.receive(c): " + ida.receive(c));
    System.out.println("ida.getStatus(): " + ida.getStatus());
    System.out.println("ida.receive(c): " + ida.receive(c));
    System.out.println("ida.getStatus(): " + ida.getStatus());
    
    System.out.println("ada.playTricks(ida): " + ada.playTricks(ida));
    System.out.println("ida.getStatus(): " + ida.getStatus());
    System.out.println("ada.getStatus(): " + ada.getStatus());
    
    System.out.println("ada.playTricks(ida): " + ada.playTricks(ida));
    System.out.println("ida.getStatus(): " + ida.getStatus());
    System.out.println("ada.getStatus(): " + ada.getStatus());
    
    try {
      ida.give(ada);
      System.out.println("-- should have error --");
    } catch(NoCandyException nce) {
      System.out.println(nce.getMessage());
    }
    
    System.out.println("idi.playTricks(adi): " + idi.playTricks(adi));
    System.out.println("idi.playTricks(adi): " + idi.playTricks(adi));
    System.out.println("idi.playTricks(adi): " + idi.playTricks(adi));
    System.out.println("idi.playTricks(adi): " + idi.playTricks(adi));
    System.out.println("idi.playTricks(adi): " + idi.playTricks(adi));
    System.out.println("idi.playTricks(adi): " + idi.playTricks(adi));
    
    System.out.println("idi.getStatus(): " + idi.getStatus());
    System.out.println("adi.getStatus(): " + adi.getStatus());
  }
}