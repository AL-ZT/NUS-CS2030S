class Test1 {
  public static void main(String[] args) {
    CS2030STest we = new CS2030STest();
    
    we.expectCompile("Candy c = new Candy() should not compile",
      "Candy c = new Candy()", false);
      
    we.expect(
      "new MysteryCandy(\"Sweet\", 2)",
      new MysteryCandy("Sweet", 2).toString(),
      "Sweet Candy (2 gram)"
    );
    we.expect(
      "new CandyBag(5, 4) // 5 candies, 4 grams each",
      new CandyBag(5, 4).toString(),
      "CandyBag 5/5 (20 gram)"
    );
    
    Candy c = new CandyBag(5, 4);
    we.expect("c.getWeight()", c.getWeight(), 20);
    we.expect("c.isEaten()", c.isEaten(), false);
    c.eat();
    we.expect("c", c.toString(), "CandyBag 4/5 (16 gram)");
    c.eat();
    c.eat();
    c.eat();
    we.expect("c", c.toString(), "CandyBag 1/5 (4 gram)");
    we.expect("c.isEaten()", c.isEaten(), false);
    c.eat();
    we.expect("c.isEaten()", c.isEaten(), true);
    we.expect("c", c.toString(), "CandyBag 0/5 (0 gram)");
    c.eat();
    we.expect("c.isEaten()", c.isEaten(), true);
    we.expect("c", c.toString(), "CandyBag 0/5 (0 gram)");
    
    Candy c0 = new MysteryCandy("Sour", 3);
    we.expect("c.isEaten()", c0.isEaten(), false);
    c0.eat();
    we.expect("c", c0.toString(), "Sour Candy (3 gram)");
    we.expectCompile("c.open() should not compile", "Candy c = new MysteryCandy(\"Sour\", 3); c.open();", false);
    
    MysteryCandy mc = (MysteryCandy) c0;
    mc.open();
    we.expect("c", c0.toString(), "Sour Candy (3 gram)");
    c0.eat();
    we.expect("c", c0.toString(), "Sour Candy (0 gram)");
    we.expect("c.isEaten()", c0.isEaten(), true);
  }
}