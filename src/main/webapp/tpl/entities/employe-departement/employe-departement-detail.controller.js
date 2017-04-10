(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDepartementDetailController', EmployeDepartementDetailController);

    EmployeDepartementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'EmployeDepartement' ];

    function EmployeDepartementDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, EmployeDepartement ) {
        var vm = this;

        vm.employeDepartement = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:employeDepartementUpdate', function(event, result) {
            vm.employeDepartement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
