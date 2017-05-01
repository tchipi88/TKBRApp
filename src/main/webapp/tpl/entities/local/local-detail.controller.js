(function() {
    'use strict';

    angular
        .module('app')
        .controller('LocalDetailController', LocalDetailController);

    LocalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Local' ,'Unite','ProduitCategorie'];

    function LocalDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Local ,Unite,ProduitCategorie) {
        var vm = this;

        vm.local = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:localUpdate', function(event, result) {
            vm.local = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
