(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitDetailController', ProduitDetailController);

    ProduitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Produit', 'ProduitCategorie', 'Unite'];

    function ProduitDetailController($scope, $rootScope, $stateParams, previousState, entity, Produit, ProduitCategorie, Unite) {
        var vm = this;

        vm.produit = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:produitUpdate', function(event, result) {
            vm.produit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
