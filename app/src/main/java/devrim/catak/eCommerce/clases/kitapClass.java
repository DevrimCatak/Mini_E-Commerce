package devrim.catak.eCommerce.clases;

public class kitapClass {
    int id;
    int adet;
    int fiyat;
    int kategori;
    int SipID;
    String isim;
    String yazar;
    int SepetAdet;
    String url;
    String aciklama;

    public kitapClass(int id,int SipId, int adet, int fiyat, int kategori, String isim, String yazar) {
        this.id = id;
        this.adet = adet;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.isim = isim;
        this.yazar = yazar;
        this.SipID = SipId;
    }

    public kitapClass(int id, int adet, int fiyat, int kategori, String isim, String yazar) {
        this.id = id;
        this.adet = adet;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.isim = isim;
        this.yazar = yazar;
    }

    public kitapClass(int id, int adet, int fiyat, int kategori, int sipID, String isim, String yazar, int sepetAdet) {
        this.id = id;
        this.adet = adet;
        this.fiyat = fiyat;
        this.kategori = kategori;
        SipID = sipID;
        this.isim = isim;
        this.yazar = yazar;
        SepetAdet = sepetAdet;
    }

    public kitapClass(int id, int adet, int fiyat, int kategori, String isim, String yazar, int sepetAdet) {
        this.id = id;
        this.adet = adet;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.isim = isim;
        this.yazar = yazar;
        SepetAdet = sepetAdet;
    }

    public kitapClass(int id, int adet, int fiyat, int kategori,  String isim, String yazar, String url, String aciklama) {
        this.id = id;
        this.adet = adet;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.isim = isim;
        this.yazar = yazar;
        this.url = url;
        this.aciklama = aciklama;
    }

    public kitapClass(int id, int adet, int fiyat, int kategori,  String isim, String yazar, String url, String aciklama,int sepetAdet) {
        this.id = id;
        this.adet = adet;
        this.fiyat = fiyat;
        this.kategori = kategori;
        this.isim = isim;
        this.yazar = yazar;
        this.url = url;
        this.aciklama = aciklama;
        SepetAdet = sepetAdet;

    }

    public int getSepetAdet() {
        return SepetAdet;
    }

    public void setSepetAdet(int sepetAdet) {
        SepetAdet = sepetAdet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public int getSipID() {
        return SipID;
    }

    public void setSipID(int sipID) {
        SipID = sipID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
        this.kategori = kategori;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        this.yazar = yazar;
    }
}
