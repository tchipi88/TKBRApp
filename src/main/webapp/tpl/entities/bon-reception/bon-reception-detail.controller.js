(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonReceptionDetailController', BonReceptionDetailController);

    BonReceptionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'BonReception' ,'Achat','Employe'];

    function BonReceptionDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, BonReception ,Achat,Employe) {
        var vm = this;

        vm.bonReception = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:bonReceptionUpdate', function(event, result) {
            vm.bonReception = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
