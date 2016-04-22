(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataViewController', SiteDataViewController);
    
    SiteDataViewController.$inject = ['$scope', '$state','$http', 'SiteDataViewDTO', 'SiteDataSearch'];
    console.log('in controller function')
    function SiteDataViewController ($scope, $state, $http, SiteDataViewDTO, SiteDataSearch) {
    	
        var vm = this;
        vm.siteDataViewDTOS = [];
        vm.loadAll = function() {
        	var data = [{name:'country', query:'China'}];
            $http.post('api/siteData/searchByFilters', data).success(function(data, status) {
                alert("uee");
            });
            /*SiteDataViewDTO.query(function(result) {
                vm.siteDataViewDTOS = result;
            });*/
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SiteDataSearch.query({query: vm.searchQuery}, function(result) {
                vm.siteDataViewDTOS = result;
            });
        };
        vm.loadAll();
     }
})();