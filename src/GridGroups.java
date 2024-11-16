//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class GridGroups {
    private int size;
    private int numberOfGroups;
    private final int[][] membership;

    public GridGroups(int var1, int var2) {
        this.size = var1;
        this.numberOfGroups = var2;
        this.membership = new int[var1][var1];
    }

    public int gridSize() {
        return this.size;
    }

    public int numberOfGroups() {
        return this.numberOfGroups;
    }

    public void addSquareToGroup(int var1, int var2, int var3) {
        this.membership[var1 - 1][var2 - 1] = var3;
    }

    public int groupOfSquare(int var1, int var2) {
        return this.membership[var1 - 1][var2 - 1];
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder();
        var1.append(this.headRow());

        for(int var2 = 1; var2 <= this.size; ++var2) {
            for(int var3 = 1; var3 <= this.size; ++var3) {
                if (this.groupOfSquare(var2, var3) != 0) {
                    var1.append(String.format("%3d", this.groupOfSquare(var2, var3)));
                } else {
                    var1.append(String.format("%3s", "."));
                }

                var1.append("");
            }

            var1.append("\n");
        }

        var1.append(this.headRow());
        return var1.toString();
    }

    private String headRow() {
        StringBuilder var1 = new StringBuilder();

        for(int var2 = 1; var2 <= this.size; ++var2) {
            var1.append("---");
        }

        var1.append("\n");
        return var1.toString();
    }
}
