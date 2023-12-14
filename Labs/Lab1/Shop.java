/**
 * This class encapsulates an event in the shop
 * simulation.  Your task is to replace this
 * class with new classes, following proper OOP principles.
 *
 * @author Lim Zheng Ting
 * @version CS2030S AY22/23 Semester 1
 */
class Shop {
  /**
   * An array to indicate if a service counter is
   * available.  available[i] is true if and only
   * if service counter i is available to serve.
   */

  private static Counter[] available;

  public Shop() {
  }

  public static int checkFirstCounter() {
    for (Counter c : Shop.available) {
      if (c.getAvailability()) {
            return c.getCounterId();
      }
    }
    return -1;
  }

  public static int getCounterByCustomerId(int customerId) {
    for (Counter c : Shop.available) {
      if (c.getCustomerId() == customerId) {
        return c.getCounterId();
      }
    }
    return -1;
  }

  public static boolean checkCounterAvailability(int counter) {
    if (counter == -1) {
      return false;
    }
    return true;
  }

  public static void markCounterUnavailable(int counterId, int customerId) {
    Shop.available[counterId].setAvailability(false);
    Shop.available[counterId].setCustomerId(customerId);
  }

  public static void markCounterAvailable(int counterId) {
    Shop.available[counterId].setAvailability(true);
  }

  public static void setShopCounters(int num) {
    Shop.available = new Counter[num];
    for (int i = 0; i < num; i++) {
      Shop.available[i] = new Counter(i, true);
    }
  }
}
