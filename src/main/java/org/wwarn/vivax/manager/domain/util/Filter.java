package org.wwarn.vivax.manager.domain.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Filter {
	
	private String name;
	private String query;
	
	public Filter() {
		super();
	}
	
	public Filter(String name, String query) {
		super();
		this.name = name;
		this.query = query;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getQuery() {
		return query;
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString() {
		return "Filter [name=" + name + ", query=" + query + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		return result;
	}
	
	public String filterQuery(List<Filter>listFilters){
		String query = "select siteData from SiteData siteData left join fetch siteData.location lo left join fetch siteData.study st left join "
		    		+ "fetch siteData.category c ";
		 for (int i=0; i<listFilters.size(); i++){
   			 if (i==0){ query += "where"+listFilters.get(i).getQuery(); }
   			 else{ query += "and"+listFilters.get(i).getQuery(); }
   		 }
   		 return query;
   	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filter other = (Filter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		return true;
	}
}


