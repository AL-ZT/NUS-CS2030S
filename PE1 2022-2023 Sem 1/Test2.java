class Test2 {
  public static void main(String[] args) throws Exception {
    CS2030STest we = new CS2030STest();
    
    Candy c = new MysteryCandy("Choco", 3);
    Treaters adi = new Treaters("Adi", c, 5);
    Trickers ida = new Trickers("Ida", "Spin", 5);
    Trickers ada = new Trickers("Ada", "Jump", 6);
    Trickers idi = new Trickers("Idi", "Boo!", 20);
    
    we.expect("c", c.toString(), "Choco Candy (3 gram)");
    we.expect("adi", adi.toString(), "Treater Adi");
    we.expect("ida", ida.toString(), "Tricker Ida");
    we.expect("ada", ada.toString(), "Tricker Ada");
    
    we.expect("ida.receive(c)", ida.receive(c), true);
    we.expect("ida.getStatus()", ida.getStatus(), "Tricker Ida performed 0 tricks and has bag size 2 remaining");
    we.expect("ida.receive(c)", ida.receive(c), false);
    we.expect("ida.getStatus()", ida.getStatus(), "Tricker Ida performed 0 tricks and has bag size 2 remaining");
    
    we.expect("ada.playTricks(ida) // Ida has candy", ada.playTricks(ida), 
    "Tricker Ada receives from Tricker Ida");
    we.expect("ida.getStatus()", ida.getStatus(), "Tricker Ida performed 0 tricks and has bag size 5 remaining");
    we.expect("ada.getStatus()", ada.getStatus(), "Tricker Ada performed 0 tricks and has bag size 3 remaining");
    
    we.expect("ada.playTricks(ida) // Ida has no candy", ada.playTricks(ida), 
    "Tricker Ada perform the trick Jump to Tricker Ida");
    we.expect("ida.getStatus()", ida.getStatus(), "Tricker Ida performed 0 tricks and has bag size 5 remaining");
    we.expect("ada.getStatus()", ada.getStatus(), "Tricker Ada performed 1 tricks and has bag size 3 remaining");
    
    try {
      ida.give(ada);
      we.expect("ida.give(ada) // should be error", "-- should be an error--", "No More Candy!");
    } catch(NoCandyException nce) {
      we.expect("ida.give(ada) // should be error", nce.getMessage(), "No More Candy!");
    }
    
    we.expect("idi.playTricks(adi)", idi.playTricks(adi), "Tricker Idi receives from Treater Adi");
    we.expect("idi.playTricks(adi)", idi.playTricks(adi), "Tricker Idi receives from Treater Adi");
    we.expect("idi.playTricks(adi)", idi.playTricks(adi), "Tricker Idi receives from Treater Adi");
    we.expect("idi.playTricks(adi)", idi.playTricks(adi), "Tricker Idi receives from Treater Adi");
    we.expect("idi.playTricks(adi)", idi.playTricks(adi), "Tricker Idi receives from Treater Adi");
    we.expect("idi.playTricks(adi)", idi.playTricks(adi), "Tricker Idi perform the trick Boo! to Treater Adi");
    
    we.expect("idi.getStatus()", idi.getStatus(), "Tricker Idi performed 1 tricks and has bag size 5 remaining");
    we.expect("adi.getStatus()", adi.getStatus(), "Treater Adi gave 5 away out of 5 Choco Candy (3 gram)");
  }
}