import java.util.PriorityQueue;

public class Spelunking {

    Block [][] cave;
    int depth;
    int Time;
    PriorityQueue<Block> pq = new PriorityQueue<>();


    public Spelunking(int depth, int[] first, int [] second, int time) {
        
        Time = time;
        this.depth = depth;
        this.cave = new Block[depth][2];    
        
        for(int i = 0; i < depth; i++) {
            cave[i][0] = new Block(first[i], i, 0);
            cave[i][1] = new Block(second[i], i, 1);
        }
    }

    private class Block implements Comparable<Block>{

        private int value;
        private boolean cleared;
        public int row;
        public int column;

        public Block(int val, int row, int column) {

            value = val;
            cleared = false;
            this.row = row;
            this.column = column;
        }

        public String getCleared() {

            if (cleared) return "yes";
            return "no";
        }

        public int getValue() {

            return value;
        }

        public void setCleared(boolean set) {

            cleared = set;
        }

        @Override
        public int compareTo(Block that) {
            if (this.value < that.value) return -1;
            if (this.value > that.value) return 1;
            return 0;
        }
    }

    public void findPath(int column, int row){

        int nextColumn = column + 1;

        int min1 = cave[nextColumn][row].getValue();
        int min2 = cave[column][(row + 1) % 2].getValue() + cave[nextColumn][(row + 1) % 2].getValue();
        int minPQ = Integer.MAX_VALUE;

        if (!pq.isEmpty()) 
            minPQ = pq.peek().getValue();

        int realMin = Math.min(Math.min(min1, min2), minPQ);

        if((Time - realMin) < 0) return;

        Time = Time - realMin;

        if (realMin == minPQ) {
            pq.poll().setCleared(true);
            // Time = Time - realMin;
            findPath(column, row);
        }

        if (realMin == min1) {

            pq.add(cave[column][(row + 1) % 2]);
            // Time = Time - realMin;
            cave[nextColumn][row].setCleared(true);
            findPath(nextColumn, row);
        }
        if (realMin == min2) {
            // pq.add(cave[nextColumn][row]);
            // Time = Time - realMin;
            cave[nextColumn][(row + 1) % 2].setCleared(true);
            cave[column][(row + 1) % 2].setCleared(true);
            findPath(nextColumn, (row + 1) % 2);
        }
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0; i < depth; i++) sb.append(cave[i][0].value + "/" + cave[i][0].getCleared() + "\t"); 
        sb.append("\n\n");
        for(int j = 0; j < depth; j++) sb.append(cave[j][1].value + "/" + cave[j][1].getCleared() +"\t");
        sb.append("\n");
        
        return sb.toString();
    }

    public static void main(String[] args) {
        
        int t = 26;

        // int [] first = {5, 1, 0, 12, 1, 4, 9};
        // int [] second = {15, 3, 7, 8, 2, 1, 2};

        int [] first = {1, 3, 5, 7, 9};
        int [] second = {2, 9, 6, 8, 10};

        int depth = first.length;

        Spelunking cave = new Spelunking(depth, first, second, t);

        cave.cave[0][0].setCleared(true);
        cave.Time = cave.Time - cave.cave[0][0].getValue();
        cave.findPath(0, 0);

        System.out.println(cave.toString());
        System.out.println("Time left: " + cave.Time);
    }
}