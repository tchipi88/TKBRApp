(function() {
    'use strict';

    angular
        .module('app')
        .controller('UniteDetailController', UniteDetailController);

    UniteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Unite' ];

    function UniteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Unite ) {
        var vm = this;

        vm.unite = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:uniteUpdate', function(event, result) {
            vm.unite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
