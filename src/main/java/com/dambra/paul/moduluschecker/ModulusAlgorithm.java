package com.dambra.paul.moduluschecker;

public enum ModulusAlgorithm {
    MOD10, MOD11, DOUBLE_ALTERNATE;

    public static ModulusAlgorithm parse(String s) throws UnknownAlgorithmException {
        switch (s){
            case "MOD10": return MOD10;
            case "MOD11": return MOD11;
            case "DBLAL": return DOUBLE_ALTERNATE;
        }
        throw new UnknownAlgorithmException("Cannot parse " + s + " as a modulus algorithm");
    }
}
