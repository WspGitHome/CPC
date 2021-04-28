package com.wanfangdata.cpc.service;

import com.wanfangdata.cpc.exception.SearchException;
import com.wanfangdata.cpc.model.*;

import java.util.List;
import java.util.Map;


public interface SearchService {

    SearchResultViewModel search(SearchViewModel model)  throws SearchException;
    SearchResultViewModel searchCache(SearchViewModel model,String cacheKey)  throws SearchException;
    FacetResultViewModel facet(FacetViewModel model)  throws SearchException;
    FacetResultViewModel facetPivot(FacetPivotViewModel model)  throws SearchException;
    Map<String,String> searchOne(String id,String collection,List<String> returnedField)  throws SearchException;
}
