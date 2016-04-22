angular.module('vivaxDataManagerApp').controller('DropDownCtrl', DropDownCtrl);

DropDownCtrl.$inject = [ '$scope', '$state', 'SiteDataViewDTO', 'SiteDataSearch' ];

function DropDownCtrl($scope, $state, SiteDataViewDTO, SiteDataSearch) {
	$scope.items = [ 'Study Type', 'Study Ref', 'Category', 'Country', 'Year Start', 'Year End', 'Upper95CI', 'PubMedId' ];

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

	$scope.appendToEl = angular.element(document
			.querySelector('#dropdown-long-content'));
};/**
 * 
 */