import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BlockChain {
  Node<Block> first;
  Node<Block> last;

  public BlockChain(int initial) throws NoSuchAlgorithmException {
    this.first.value = new Block(0, initial, new Hash(null));
    this.last.value = this.first.value;
  }

  public Block mine(int amount) throws NoSuchAlgorithmException {
    return new Block(this.last.value.getNum() + 1, amount, this.last.value.getHash());
  }

  public int getSize() {
    return last.value.getNum() + 1;
  }

  public void append(Block blk) throws NoSuchAlgorithmException {
    Hash h = blk.createHash(blk.getNum(), blk.getAmount(), last.value.getHash(), blk.getNonce());
    if(!(blk.getHash().equals(h)) || !(h.isValid())) {
      throw new IllegalArgumentException();
    }
    Node<Block> newBlock = new Node<Block>(blk, null);
    if (this.first == this.last) {
      this.last = newBlock;
      this.first.next = last;
    } else {
      this.last.next = newBlock;
      this.last = newBlock;
    }
  }

  public boolean removeLast() {
    if (this.last.value.getNum() == 0) {
      return false;
    } else {
      BCIterator<Block> i = new BCIterator<Block>(first);
      while(i.hasNext()) {
        
      }

    }
  }

  public Hash getHash() {
    return last.value.getHash();
  }

  public boolean isValidBlockChain() throws NoSuchAlgorithmException {
    Node<Block> temp = first;
    while (temp != null) {
      if (!temp.value.getHash().equals(temp.value.createHash(temp.value.getNum(),
          temp.value.getAmount(), temp.value.getPrevHash(), temp.value.getNonce()))) {
        return false;
      } else if (!temp.value.getHash().isValid()) {
        return false;
      }
      temp = temp.next;
    }
    return true;
  }


  public String toString() {
    String s = "";
    Node temp = first;
    for (int i = 0; i < getSize(); i++) {
      s += (temp.value.toString() + "\n");
      temp = temp.next;
    }
    return s;
  }
}
/**
 * Node code was taken from code given in class
 */
class Node<T> {
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The value stored in the ndoe.
   */
  T value;

  /**
   * The next node.
   */
  Node<T> next;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new node that contains val and that links to next.
   */
  public Node(T value, Node<T> next) {
    this.value = value;
    this.next = next;
  } // Node(T, Node<T>)
} // class Node<T>

/*
 * borrowed code from iterator reading
 */
class BCIterator<T> implements Iterator<T> {
  public Node<T> current;
  public BCIterator(Node<T> start) {
    this.current = start;
  } // LSIterator(Node<T>)

  @Override
  public T next() throws NoSuchElementException {
    if (! this.hasNext()) {
      throw new NoSuchElementException();
    }
    T val = this.current.value;
    this.current = this.current.next;
    return val;
  } // next()

  @Override
  public boolean hasNext() {
    return this.current != null;
    
  } // hasNext()
} // LSIterator<T>    

