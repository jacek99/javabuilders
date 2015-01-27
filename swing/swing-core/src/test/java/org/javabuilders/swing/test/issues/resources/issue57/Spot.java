package org.javabuilders.swing.test.issues.resources.issue57;


/**
 * Spot.
 *
 * @author anavarro - 31 Jul 2009
 *
 */
public final class Spot
{
    private String stockName = "";
    private Double bid = new Double(1);
    private Double ask = new Double(2);

    /**
     * getStockName.
     *
     * @return String
     */
    @ABeanTableModelProperty(name = "Feed Code", orderKey = "AA")
    public String getStockName()
    {
        return this.stockName;
    }

    /**
     * setStockName.
     *
     * @param aStockName
     */
    public void setStockName(final String aStockName)
    {
        this.stockName = aStockName;
    }

    /**
     * getBid.
     *
     * @return Double
     */
    @ABeanTableModelProperty(name = "Bid", orderKey = "AB")
    public Double getBid()
    {
        return this.bid;
    }

    /**
     * setBid.
     *
     * @param aBid
     */
    public void setBid(final Double aBid)
    {
        this.bid = aBid;
    }

    /**
     * getAsk.
     *
     * @return Double
     */
    @ABeanTableModelProperty(name = "Bid", orderKey = "AB")
    public Double getAsk()
    {
        return this.ask;
    }

    /**
     * setAsk.
     *
     * @param aAsk
     */
    public void setAsk(final Double aAsk)
    {
        this.ask = aAsk;
    }


}
