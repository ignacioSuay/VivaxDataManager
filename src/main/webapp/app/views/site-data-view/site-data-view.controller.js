(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataViewController', SiteDataViewController);

    SiteDataViewController.$inject = ['$scope', '$state', 'SiteDataViewDTO', 'SiteDataSearch'];
    console.log('in controller function')
    function SiteDataViewController ($scope, $state, SiteDataViewDTO, SiteDataSearch) {

       $scope.filters = [{name:'', query:'' }];

         $scope.addFilter= function(){
             $scope.filters.push({});
         };

        $scope.siteDataViewDTOS = [];
        $scope.loadAll = function() {
            var data = [{name:'country', query:'China'}];
            $scope.siteDataViewDTOS = SiteDataViewDTO.query(data);
        };

        $scope.search = function () {
            if (!$scope.searchQuery) {
                return $scope.loadAll();
            }
            SiteDataSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.siteDataViewDTOS = result;
            });
        };

        $scope.loadAll();

        $scope.names = [ 'Study Type', 'Study Ref', 'Category', 'Country', 'Year Start', 'Year End', 'Upper95CI', 'PubMedId', 'Treatments' ];
        
        $scope.status = {
            isopen : false
        };

        $scope.toggled = function(open) {
            console.log('Dropdown is now: ', open);
        };

        $scope.toggleDropdown = function($event) {
            $event.preventDefault();
            $event.stopPropagation();
            $scope.status.isopen = !$scope.status.isopen;
        };

        $scope.filters = [{name:'Filters', query:'' }];

        $scope.pickFilterName = function(nameBody){
          $scope.name = nameBody;
          console.log($scope.name);
        };

        $scope.pickFilterQuery = function(queryBody){
            console.log($scope.name);
            $scope.query = queryBody;
            var filter = {name:$scope.name, query:$scope.query };
            $scope.filters.push(filter);
            alert(JSON.stringify($scope.filters));
        };

        $scope.appendToEl = angular.element(document
			.querySelector('#dropdown-long-content'));
     }


})();
