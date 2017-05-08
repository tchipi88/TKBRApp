(function() {
    'use strict';

    angular
        .module('app')
        .controller('PaieDetailController', PaieDetailController);

    PaieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Paie' ,'Employe'];

    function PaieDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Paie ,Employe) {
        var vm = this;

        vm.paie = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:paieUpdate', function(event, result) {
            vm.paie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
