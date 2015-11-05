package com.probe.open.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT_DISTINCT;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.probe.open.entity.PostRouter;
import com.probe.open.entity.PostRouterExample.Criteria;
import com.probe.open.entity.PostRouterExample.Criterion;
import com.probe.open.entity.PostRouterExample;
import java.util.List;
import java.util.Map;

public class PostRouterSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String countByExample(PostRouterExample example) {
        BEGIN();
        SELECT("count(*)");
        FROM("wi_probe_open_post_router");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String deleteByExample(PostRouterExample example) {
        BEGIN();
        DELETE_FROM("wi_probe_open_post_router");
        applyWhere(example, false);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String insertSelective(PostRouter record) {
        BEGIN();
        INSERT_INTO("wi_probe_open_post_router");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getProbesn() != null) {
            VALUES("probeSn", "#{probesn,jdbcType=VARCHAR}");
        }
        
        if (record.getProbemac() != null) {
            VALUES("probeMac", "#{probemac,jdbcType=VARCHAR}");
        }
        
        if (record.getThirdid() != null) {
            VALUES("thirdId", "#{thirdid,jdbcType=INTEGER}");
        }
        
        if (record.getGmtcreated() != null) {
            VALUES("gmtCreated", "#{gmtcreated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtmodified() != null) {
            VALUES("gmtModified", "#{gmtmodified,jdbcType=TIMESTAMP}");
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String selectByExample(PostRouterExample example) {
        BEGIN();
        if (example != null && example.isDistinct()) {
            SELECT_DISTINCT("id");
        } else {
            SELECT("id");
        }
        SELECT("probeSn");
        SELECT("probeMac");
        SELECT("thirdId");
        SELECT("gmtCreated");
        SELECT("gmtModified");
        FROM("wi_probe_open_post_router");
        applyWhere(example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            ORDER_BY(example.getOrderByClause());
        }
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        PostRouter record = (PostRouter) parameter.get("record");
        PostRouterExample example = (PostRouterExample) parameter.get("example");
        
        BEGIN();
        UPDATE("wi_probe_open_post_router");
        
        if (record.getId() != null) {
            SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getProbesn() != null) {
            SET("probeSn = #{record.probesn,jdbcType=VARCHAR}");
        }
        
        if (record.getProbemac() != null) {
            SET("probeMac = #{record.probemac,jdbcType=VARCHAR}");
        }
        
        if (record.getThirdid() != null) {
            SET("thirdId = #{record.thirdid,jdbcType=INTEGER}");
        }
        
        if (record.getGmtcreated() != null) {
            SET("gmtCreated = #{record.gmtcreated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtmodified() != null) {
            SET("gmtModified = #{record.gmtmodified,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String updateByExample(Map<String, Object> parameter) {
        BEGIN();
        UPDATE("wi_probe_open_post_router");
        
        SET("id = #{record.id,jdbcType=INTEGER}");
        SET("probeSn = #{record.probesn,jdbcType=VARCHAR}");
        SET("probeMac = #{record.probemac,jdbcType=VARCHAR}");
        SET("thirdId = #{record.thirdid,jdbcType=INTEGER}");
        SET("gmtCreated = #{record.gmtcreated,jdbcType=TIMESTAMP}");
        SET("gmtModified = #{record.gmtmodified,jdbcType=TIMESTAMP}");
        
        PostRouterExample example = (PostRouterExample) parameter.get("example");
        applyWhere(example, true);
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    public String updateByPrimaryKeySelective(PostRouter record) {
        BEGIN();
        UPDATE("wi_probe_open_post_router");
        
        if (record.getProbesn() != null) {
            SET("probeSn = #{probesn,jdbcType=VARCHAR}");
        }
        
        if (record.getProbemac() != null) {
            SET("probeMac = #{probemac,jdbcType=VARCHAR}");
        }
        
        if (record.getThirdid() != null) {
            SET("thirdId = #{thirdid,jdbcType=INTEGER}");
        }
        
        if (record.getGmtcreated() != null) {
            SET("gmtCreated = #{gmtcreated,jdbcType=TIMESTAMP}");
        }
        
        if (record.getGmtmodified() != null) {
            SET("gmtModified = #{gmtmodified,jdbcType=TIMESTAMP}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wi_probe_open_post_router
     *
     * @mbggenerated Wed Nov 04 21:15:06 CST 2015
     */
    protected void applyWhere(PostRouterExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            WHERE(sb.toString());
        }
    }
}