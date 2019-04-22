public enum TechLevel {
    PREAGRICULTURE(0),
    AGRICULTURE(1),
    Medieval(2),
    Renaissance(3),
    EarlyIndustrial(4),
    Industrial(5),
    PostIndustrial(6),
    HiTech(7);

    private final int rank;

    TechLevel(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name();
    }

    public int getRank(){
        return rank;
    }
}
