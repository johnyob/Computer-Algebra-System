package helpers;

import java.util.ArrayList;


/**
 *
 * @author Alistair O'Brien
 * @author Nathan Shaw
 */
public class List<Type> {

  private ArrayList<Type> items;

  public List(ArrayList<Type> items){
    this.items = items;
  }

  public ArrayList<Type> getItems(){
    return items;
  }

  public boolean isEmpty(){
    return items.isEmpty();
  }

  public int size(){
    return items.size();
  }

  public void push(Type n){
    items.add(0, n);
  }

  public Type head() {
    return items.get(0);
  }

  public List<Type> tail() {
    return new List<Type>(new ArrayList<Type>(items.subList(1, size())));
  }

  public boolean equals(Object other) {
    if (!(other instanceof List)) return false;

    if (isEmpty() && ((List) other).isEmpty()) return true;
    if (isEmpty() || ((List) other).isEmpty()) return false;
    if (head().equals(((List) other).head())) {
      List<Type> a = tail();
      List<Type> b = ((List) other).tail();
      return a.equals(b);
    }

    return false;
  }

  public Type[] toArray() {
    return (Type[]) items.toArray();
  }
}
