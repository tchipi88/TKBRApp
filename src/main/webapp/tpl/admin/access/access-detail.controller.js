(function() {
    'use strict';

    angular
        .module('app')
        .controller('AccessDetailController', AccessDetailController);

    AccessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Access' ];

    function AccessDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Access ) {
        var vm = this;

        vm.access = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:accessUpdate', function(event, result) {
            vm.access = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
