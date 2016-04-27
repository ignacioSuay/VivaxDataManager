(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataViewController', SiteDataViewController);

    SiteDataViewController.$inject = ['$scope', '$state', 'SiteDataViewDTO', 'SiteDataSearch'];
    console.log('in controller function')
    function SiteDataViewController ($scope, $state, SiteDataViewDTO, SiteDataSearch) {

        //$scope.names = [ 'Study Type', 'Study Ref', 'Category', 'Country', 'Year Start', 'Year End', 'Upper95CI', 'PubMedId', 'Treatments' ];
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
            var data = [{name: 'country', query: 'China'}];
            $scope.siteDataViewDTOS = SiteDataViewDTO.query(data);
        };
        $scope.loadAll();

        $scope.search = function () {
            //if (!$scope.searchQuery) {
            //    return $scope.loadAll();
            //}
            alert(JSON.stringify($scope.filters));
            SiteDataSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.siteDataViewDTOS = result;
            });
        };

        $scope.pickFilterQuery = function (queryBody) {
            console.log($scope.name);
            $scope.query = queryBody;
            var filter = {name: $scope.name, query: $scope.query};
            $scope.filters.push(filter);
            alert(JSON.stringify($scope.filters));
        };

    }


})();
