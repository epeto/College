import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    List<Node<T>> children = new ArrayList<Node<T>>();
    Node<T> parent = null;
    T data = null;
    //Esta variable nos ayudará en el algoritmo MiniMax.
    float valor=0;

    public Node(T data) {
        this.data = data;
    }

    public Node(T data, Node<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setParent(Node<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public void addChild(T data) {
        Node<T> child = new Node<T>(data);
        child.setParent(this);
    }

    public void addChild(Node<T> child) {
        this.children.add(child);
        child.parent = this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        if(this.children.size() == 0) 
            return true;
        else 
            return false;
    }

    public void removeParent() {
        this.parent = null;
    }
    
    public float getValor(){
      return valor;
    }
}