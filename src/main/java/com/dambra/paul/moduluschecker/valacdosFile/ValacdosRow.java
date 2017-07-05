package com.dambra.paul.moduluschecker.valacdosFile;

public final class ValacdosRow {
    private final SortCodeRange sortCodeRange;
    private final WeightRow weightRow;

    public ValacdosRow(SortCodeRange sortCodeRange, WeightRow weightRow) {
        this.sortCodeRange = sortCodeRange;
        this.weightRow = weightRow;
    }

    SortCodeRange getSortCodeRange() {
        return sortCodeRange;
    }

    WeightRow getWeightRow() {
        return weightRow;
    }
}
