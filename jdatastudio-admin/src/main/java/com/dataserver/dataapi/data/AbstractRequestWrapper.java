package com.dataserver.dataapi.data;

import com.dataserver.api.Filter;
import com.dataserver.api.Pagination;
import com.dataserver.api.QueryParams;
import com.dataserver.api.Sort;
import com.dataserver.dataapi.Constants;

import java.util.List;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2018-11-02
 */
public abstract class AbstractRequestWrapper {
    protected abstract Pagination wrapPagination(Map<String, Object> requestParams);

    protected abstract List<Sort> wrapSort(Map<String, Object> requestParams);

    protected abstract String[] wrapSelect(Map<String, Object> requestParams);

    protected abstract List<Filter> wrapFilters(Map<String, Object> requestParams);

    protected String wrapQ(Map<String, Object> requestParams){
        String q = (String)requestParams.get(Constants.q);
        requestParams.remove(Constants.q);
        return q;
    }

    public QueryParams wrapQueryParams(Map<String, Object> requestParams) {
        requestParams.remove(Constants.ApiStandard);
        return QueryParams.builder()
                .q(wrapQ(requestParams))
                .filters(wrapFilters(requestParams))
                .pagination(wrapPagination(requestParams))
                .sorts(wrapSort(requestParams))
                .select(wrapSelect(requestParams))
                .build();
    }

}
