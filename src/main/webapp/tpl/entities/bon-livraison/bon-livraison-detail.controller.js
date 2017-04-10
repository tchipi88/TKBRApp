(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonLivraisonDetailController', BonLivraisonDetailController);

    BonLivraisonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'BonLivraison' ,'Vente','Employe'];

    function BonLivraisonDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, BonLivraison ,Vente,Employe) {
        var vm = this;

        vm.bonLivraison = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:bonLivraisonUpdate', function(event, result) {
            vm.bonLivraison = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
