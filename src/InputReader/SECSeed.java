package InputReader;

/**
 * Created by xli1 on 8/12/15.
 */
public class SECSeed extends Seed {
  public SECSeed(String url) {
    super(url);
  }

  private String companyName;
  private String cik;
  private String liCompanyID;
  private String filingYear;
  private String filingQuarter;

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getCik() {
    return cik;
  }

  public void setCik(String cik) {
    this.cik = cik;
  }

  public String getLiCompanyID() {
    return liCompanyID;
  }

  public void setLiCompanyID(String liCompanyID) {
    this.liCompanyID = liCompanyID;
  }

  public String getFilingYear() {
    return filingYear;
  }

  public void setFilingYear(String filingYear) {
    this.filingYear = filingYear;
  }

  public String getFilingQuarter() {
    return filingQuarter;
  }

  public void setFilingQuarter(String filingQuarter) {
    this.filingQuarter = filingQuarter;
  }
}
