package cs2030s.fp;
import java.util.NoSuchElementException;

/**
 * CS2030S PE2 
 * AY2022/23 Semester 1.
 *
 * NOTE: We do not require inner/nested class.
 *       This design is based on good practice
 *       where we have 2 non-disjoint state
 *       unlike Probably/Actually/etc.
 *       This can be represented by a lattice:
 *
 *                 Both
 *                /    \
 *            Undo      Redo
 *                \    /
 *                 None
 *
 *       Your design should think about what
 *       would undo/redo/map/flatMap do in
 *       terms of these state (e.g., undo may
 *       make it redoable only or still both).
 * 
 * @author Your Student Number
 **/
public abstract class Saveable<T> {
  protected final T val;
  private Saveable(T val) {
    this.val = val;
  }
  
  public static <T> Saveable<T> of(T val) {
    return new Noneable<>(val);
  }
  
  public Saveable<T> undo() {
    throw new NoSuchElementException();
  }
  public Saveable<T> redo() {
    throw new NoSuchElementException();
  }
  public Saveable<T> map(Immutator<? extends T, ? super T> fn) {
    return new Undoable<>(fn.invoke(this.val), this);
  }
  public Saveable<T> flatMap1(Immutator<? extends Saveable<? extends T>, ? super T> fn) {
    Saveable<? extends T> tmp = fn.invoke(this.val);
    // Not the best practice because
    //   "do not use try-catch as control-flow"
    // To make it better, add methods like
    // isUndoable() and isRedoable()
    try {
      return new Bothable<T>(tmp.val, tmp.undo(), tmp.redo());
    } catch(NoSuchElementException e) {}
    try {
      return new Undoable<T>(tmp.val, tmp.undo());
    } catch(NoSuchElementException e) {}
    try {
      return new Redoable<T>(tmp.val, tmp.redo());
    } catch(NoSuchElementException e) {}
    return new Noneable<T>(tmp.val);
  }
  public Saveable<T> flatMap2(Immutator<? extends Saveable<? extends T>, ? super T> fn) {
    Saveable<? extends T> tmp = fn.invoke(this.val);
    return new Undoable<T>(tmp.val, this);
  }
  
  @Override
  public String toString() {
    return "Saveable[" + this.val + "]";
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Saveable<?>)) {
      return false;
    }
    Saveable<?> save = (Saveable<?>) obj;
    if (this.val == null) {
      // optional: we do not test NULL
      return save.val == null;
    }
    return this.val.equals(save.val);
  }
  
  // Can neither undo nor redo
  private static class Noneable<T> extends Saveable<T> {
    private Noneable(T val) {
      super(val);
    }
  }
  // Can undo only
  private static class Undoable<T> extends Saveable<T> {
    private final Saveable<? extends T> prev;
    private Undoable(T val, Saveable<? extends T> prev) {
      super(val);
      this.prev = prev;
    }
    
    @Override
    public Saveable<T> undo() {
      try {
        // can undo + redo
        return new Bothable<>(this.prev.val, this.prev.undo(), this);
      } catch(NoSuchElementException e) {
        // can only redo
        return new Redoable<>(this.prev.val, this);
      }
    }
  }
  // Can redo only
  private static class Redoable<T> extends Saveable<T> {
    private final Saveable<? extends T> next;
    private Redoable(T val, Saveable<? extends T> next) {
      super(val);
      this.next = next;
    }
    
    @Override
    public Saveable<T> redo() {
      try {
        // can undo + redo
        return new Bothable<>(this.next.val, this, this.next.redo());
      } catch(NoSuchElementException e) {
        // can only undo
        return new Undoable<>(this.next.val, this);
      }
    }
  }
  // Can both undo and redo
  private static class Bothable<T> extends Saveable<T> {
    private final Saveable<? extends T> prev;
    private final Saveable<? extends T> next;
    private Bothable(T val, Saveable<? extends T> prev, Saveable<? extends T> next) {
      super(val);
      this.prev = prev;
      this.next = next;
    }
    
    @Override
    public Saveable<T> undo() {
      try {
        // can undo + redo
        return new Bothable<>(this.prev.val, this.prev.undo(), this);
      } catch(NoSuchElementException e) {
        // can only redo
        return new Redoable<>(this.prev.val, this);
      }
    }
    @Override
    public Saveable<T> redo() {
      try {
        // can undo + redo
        return new Bothable<>(this.next.val, this, this.next.redo());
      } catch(NoSuchElementException e) {
        // can only undo
        return new Undoable<>(this.next.val, this);
      }
    }
  }  
}