package devrim.catak.eCommerce.clases;

public class ana {
    String url;
    String select;
    String where;

    public ana(String url, String select, String where) {
        this.url = url;
        this.select = select;
        this.where = where;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }


}
