//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class SumdokuGrid {
    private final int[][] grid;
    private int size;

    public SumdokuGrid(int var1) {
        this.size = var1;
        this.grid = new int[var1][var1];
    }

    public int size() {
        return this.size;
    }

    public int value(int var1, int var2) {
        return this.grid[var1 - 1][var2 - 1];
    }

    public boolean isFilled(int var1, int var2) {
        return this.grid[var1 - 1][var2 - 1] != 0;
    }

    public void fill(int var1, int var2, int var3) {
        this.grid[var1 - 1][var2 - 1] = var3;
    }

    public String toString() {
        StringBuilder var1 = new StringBuilder(this.headRow());

        for(int var2 = 1; var2 <= this.size; ++var2) {
            for(int var3 = 1; var3 <= this.size; ++var3) {
                if (this.isFilled(var2, var3)) {
                    var1.append(this.value(var2, var3));
                } else {
                    var1.append(".");
                }

                var1.append(" ");
            }

            var1.append("\n");
        }

        var1.append(this.headRow());
        return var1.toString();
    }

    private String headRow() {
        StringBuilder var1 = new StringBuilder();

        for(int var2 = 1; var2 <= this.size; ++var2) {
            var1.append("--");
        }

        var1.append("\n");
        return var1.toString();
    }
}
