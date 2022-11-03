public class HarmZadan implements Comparable<HarmZadan>{
    public int getCzasNaPierMasz() {
        return czasNaPierMasz;
    }

    public void setCzasNaPierMasz(int czasNaPierMasz) {
        this.czasNaPierMasz = czasNaPierMasz;
    }

    public int getCzasNaDruMasz() {
        return czasNaDruMasz;
    }

    public void setCzasNaDruMasz(int czasNaDruMasz) {
        this.czasNaDruMasz = czasNaDruMasz;
    }

    private int czasNaPierMasz;
    private int czasNaDruMasz;

    @Override
    public int compareTo(HarmZadan o) {
        if(czasNaPierMasz > o.getCzasNaPierMasz()){
            return 1;
        }else if(o.getCzasNaPierMasz() == czasNaPierMasz){
            return 0;
        }else return -1;
    }
}
