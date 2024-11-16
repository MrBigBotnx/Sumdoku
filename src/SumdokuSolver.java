//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SumdokuSolver {
    private SumdokuSolution originalSolution;
    private int searchLimitSolutions;
    private int solutionsFound;
    private int gridSize;

    public SumdokuSolver(SumdokuGrid var1, GridGroups var2) {
        this.gridSize = var1.size();
        int[][] var3 = new int[this.gridSize][this.gridSize];
        int[] var4 = new int[var2.numberOfGroups()];
        int[] var5 = new int[var2.numberOfGroups()];

        for(int var6 = 1; var6 <= var1.size(); ++var6) {
            for(int var7 = 1; var7 <= var1.size(); ++var7) {
                var3[var6 - 1][var7 - 1] = var2.groupOfSquare(var6, var7) - 1;
                ++var5[var3[var6 - 1][var7 - 1]];
                var4[var3[var6 - 1][var7 - 1]] += var1.value(var6, var7);
            }
        }

        int[][] var10 = new int[this.gridSize][this.gridSize];

        for(int var11 = 0; var11 < var4.length; ++var11) {
            if (var5[var11] == 1) {
                for(int var8 = 0; var8 < var3.length; ++var8) {
                    for(int var9 = 0; var9 < var3.length; ++var9) {
                        if (var3[var8][var9] == var11) {
                            var10[var8][var9] = var4[var11];
                        }
                    }
                }
            }
        }

        this.originalSolution = new SumdokuSolution(var10, var3, var4);
    }

    public int howManySolutions(int var1) {
        SumdokuSolution var3 = new SumdokuSolution(this.originalSolution);
        this.solutionsFound = 0;
        this.searchLimitSolutions = var1;
        Set var2 = this.findSolutions(var3);
        return var2.size();
    }

    private Set<SumdokuSolution> findSolutions(SumdokuSolution var1) {
        return this.findSolutions(var1, 0);
    }

    private Set<SumdokuSolution> findSolutions(SumdokuSolution var1, int var2) {
        HashSet var3 = new HashSet();
        if (this.solutionsFound >= this.searchLimitSolutions) {
            return var3;
        } else if (var2 == this.gridSize * this.gridSize) {
            var3.add(new SumdokuSolution(var1));
            ++this.solutionsFound;
            return var3;
        } else {
            int var4 = var2 / this.gridSize;
            int var5 = var2 % this.gridSize;
            if (var1.isFilled(var4, var5)) {
                return this.findSolutions(var1, var2 + 1);
            } else {
                for(Integer var8 : var1.getOptionsForCell(var4, var5)) {
                    SumdokuSolution var9 = new SumdokuSolution(var1);
                    var9.setCellValue(var4, var5, var8);
                    var3.addAll(this.findSolutions(var9, var2 + 1));
                }

                return var3;
            }
        }
    }

    private class SumdokuSolution {
        private Integer[][] board;
        private int size;
        private int[][] g;
        private int[] groupCardinality;
        private int[] groupValues;
        private int nrGroups;

        SumdokuSolution(int[][] var2, int[][] var3, int[] var4) {
            this.size = var2.length;
            this.board = new Integer[this.size][this.size];

            for(int var5 = 0; var5 < this.size; ++var5) {
                for(int var6 = 0; var6 < this.size; ++var6) {
                    this.board[var5][var6] = var2[var5][var6] != 0 ? var2[var5][var6] : null;
                }
            }

            this.nrGroups = var4.length;
            this.g = var3;
            this.groupValues = var4;
            this.groupCardinality = new int[this.nrGroups];

            for(int var7 = 0; var7 < var3.length; ++var7) {
                for(int var8 = 0; var8 < var3.length; ++var8) {
                    int var10002 = this.groupCardinality[var3[var7][var8]]++;
                }
            }

        }

        public SumdokuSolution(SumdokuSolution var2) {
            this.size = var2.size;
            this.board = new Integer[this.size][this.size];

            for(int var3 = 0; var3 < this.size; ++var3) {
                for(int var4 = 0; var4 < this.size; ++var4) {
                    this.board[var3][var4] = var2.board[var3][var4];
                }
            }

            this.g = var2.g;
            this.groupValues = var2.groupValues;
            this.groupCardinality = var2.groupCardinality;
            this.nrGroups = var2.nrGroups;
        }

        public boolean isFilled(int var1, int var2) {
            return this.board[var1][var2] != null;
        }

        public List<Integer> getOptionsForCell(int var1, int var2) {
            ArrayList var3 = new ArrayList();

            for(int var4 = 1; var4 <= this.size; ++var4) {
                var3.add(var4);
            }

            this.removeOptionsUsedInRow(var3, var1);
            this.removeOptionsUsedInColumn(var3, var2);
            this.removeOptionsThatMakeGroupSumImpossible(var3, var1, var2);
            return var3;
        }

        private void removeOptionsThatMakeGroupSumImpossible(List<Integer> var1, int var2, int var3) {
            ArrayList var4 = new ArrayList();

            for(int var6 : var1) {
                if (this.notFilledCellsInGroup(this.g[var2][var3]) == 1) {
                    if (var6 + this.groupSum(this.g[var2][var3]) != this.groupValues[this.g[var2][var3]]) {
                        var4.add(var6);
                    }
                } else if (var6 + this.groupSum(this.g[var2][var3]) > this.groupValues[this.g[var2][var3]]) {
                    var4.add(var6);
                }
            }

            var1.removeAll(var4);
        }

        private int notFilledCellsInGroup(int var1) {
            int var2 = 0;

            for(int var3 = 0; var3 < this.size; ++var3) {
                for(int var4 = 0; var4 < this.size; ++var4) {
                    if (this.g[var3][var4] == var1 && this.board[var3][var4] == null) {
                        ++var2;
                    }
                }
            }

            return var2;
        }

        private int groupSum(int var1) {
            int var2 = 0;

            for(int var3 = 0; var3 < this.size; ++var3) {
                for(int var4 = 0; var4 < this.size; ++var4) {
                    if (this.g[var3][var4] == var1 && this.board[var3][var4] != null) {
                        var2 += this.board[var3][var4];
                    }
                }
            }

            return var2;
        }

        private void removeOptionsUsedInColumn(List<Integer> var1, int var2) {
            for(int var3 = 0; var3 < this.size; ++var3) {
                var1.remove(this.board[var3][var2]);
            }

        }

        private void removeOptionsUsedInRow(List<Integer> var1, int var2) {
            for(int var3 = 0; var3 < this.size; ++var3) {
                var1.remove(this.board[var2][var3]);
            }

        }

        public void setCellValue(int var1, int var2, int var3) {
            this.board[var1][var2] = var3;
        }

        public String toString() {
            StringBuilder var1 = new StringBuilder();

            for(Integer[] var5 : this.board) {
                for(Integer var9 : var5) {
                    var1.append(var9 != null ? var9.toString() : ".");
                }

                var1.append("\n");
            }

            return var1.toString();
        }
    }
}
