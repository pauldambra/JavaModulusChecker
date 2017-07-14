package com.github.pauldambra.moduluschecker.valacdosFile;

public final class ValacdosRow {
    final SortCodeRange sortCodeRange;
    final WeightRow weightRow;

    public ValacdosRow(SortCodeRange sortCodeRange, WeightRow weightRow) {
        this.sortCodeRange = sortCodeRange;
        this.weightRow = weightRow;
    }
}
