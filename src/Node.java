public class Node {
    private int h;
    private int g;
    private int f;
    private final Point point;
    private Node ParentNode;
    private boolean current = false;

    public Node(Point point){
        this.point = point;
    }

    public Node(Point point, int g, int h, int f, Node parentNode){
        this.point = point;
        this.g = g;
        this.h = h;
        this.f = f;
        this.ParentNode = parentNode;
    }

    public int getH() {
        return h;
    }

    public int getG() {
        return g;
    }

    public int getF() {
        return f;
    }

    public Point getPoint() {
        return point;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getParentNode() {
        return ParentNode;
    }

    public void setParentNode(Node parentNode) {
        ParentNode = parentNode;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
