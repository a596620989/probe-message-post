package com.probe.open.entity;

import java.util.Date;

public class PostRouter {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wi_probe_open_post_router.id
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wi_probe_open_post_router.probeSn
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    private String probesn;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wi_probe_open_post_router.probeMac
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    private String probemac;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wi_probe_open_post_router.thirdId
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    private Integer thirdid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wi_probe_open_post_router.gmtCreated
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    private Date gmtcreated;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wi_probe_open_post_router.gmtModified
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    private Date gmtmodified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wi_probe_open_post_router.id
     *
     * @return the value of wi_probe_open_post_router.id
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wi_probe_open_post_router.id
     *
     * @param id the value for wi_probe_open_post_router.id
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wi_probe_open_post_router.probeSn
     *
     * @return the value of wi_probe_open_post_router.probeSn
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String getProbesn() {
        return probesn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wi_probe_open_post_router.probeSn
     *
     * @param probesn the value for wi_probe_open_post_router.probeSn
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public void setProbesn(String probesn) {
        this.probesn = probesn == null ? null : probesn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wi_probe_open_post_router.probeMac
     *
     * @return the value of wi_probe_open_post_router.probeMac
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String getProbemac() {
        return probemac;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wi_probe_open_post_router.probeMac
     *
     * @param probemac the value for wi_probe_open_post_router.probeMac
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public void setProbemac(String probemac) {
        this.probemac = probemac == null ? null : probemac.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wi_probe_open_post_router.thirdId
     *
     * @return the value of wi_probe_open_post_router.thirdId
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public Integer getThirdid() {
        return thirdid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wi_probe_open_post_router.thirdId
     *
     * @param thirdid the value for wi_probe_open_post_router.thirdId
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public void setThirdid(Integer thirdid) {
        this.thirdid = thirdid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wi_probe_open_post_router.gmtCreated
     *
     * @return the value of wi_probe_open_post_router.gmtCreated
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public Date getGmtcreated() {
        return gmtcreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wi_probe_open_post_router.gmtCreated
     *
     * @param gmtcreated the value for wi_probe_open_post_router.gmtCreated
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public void setGmtcreated(Date gmtcreated) {
        this.gmtcreated = gmtcreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wi_probe_open_post_router.gmtModified
     *
     * @return the value of wi_probe_open_post_router.gmtModified
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public Date getGmtmodified() {
        return gmtmodified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wi_probe_open_post_router.gmtModified
     *
     * @param gmtmodified the value for wi_probe_open_post_router.gmtModified
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public void setGmtmodified(Date gmtmodified) {
        this.gmtmodified = gmtmodified;
    }
}