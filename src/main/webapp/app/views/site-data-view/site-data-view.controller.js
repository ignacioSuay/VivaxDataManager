(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataViewController', SiteDataViewController);

    SiteDataViewController.$inject = ['$scope', '$state', 'SiteDataViewDTO', 'SiteDataSearch'];
    
    function SiteDataViewController ($scope, $state, SiteDataViewDTO, SiteDataSearch) {
            
        $scope.names = [
            {text: "Study Type", type: "text"},
            {text: "Study Ref", type: "text"},
            {text: "Category", type: "text"},
            {text: "Country", type: "text"},
            {text: "Year Start", type: "text"},
            {text: "Year End", type: "text"},
            {text: "Upper95CI", type:"text"},
            {text: "PubMedId", type:"text"},
            {text: "Treatments", type:"text"}];

        $scope.filters = [{}];
        $scope.siteDataViewDTOS = [];

        $scope.addFilter = function () {
            $scope.filters.push({});
        };

        $scope.removeFilter = function (index) {
            $scope.filters.splice(index, 1);
        };

        $scope.loadAll = function () {
            var data = [{name:'', query:''}];
            $scope.siteDataViewDTOS = SiteDataViewDTO.query(data);
        };
        $scope.loadAll();

        $scope.search = function () {
            var filterDTO = $scope.formatFilters();
            SiteDataViewDTO.query(filterDTO, function (result) {
                $scope.siteDataViewDTOS = result;
            });
        };

        $scope.formatFilters = function(){
            var filterDTO = [];
            $scope.filters.forEach(function(filter){
                var text = filter.name.text.replace(/[\s]/g, '');
                filterDTO.push({name: text, query: filter.query});
            });
            return filterDTO;
        };
    }
})();
