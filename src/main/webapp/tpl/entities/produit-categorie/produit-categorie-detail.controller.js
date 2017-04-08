(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitCategorieDetailController', ProduitCategorieDetailController);

    ProduitCategorieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProduitCategorie'];

    function ProduitCategorieDetailController($scope, $rootScope, $stateParams, previousState, entity, ProduitCategorie) {
        var vm = this;

        vm.produitCategorie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tkbrApp:produitCategorieUpdate', function(event, result) {
            vm.produitCategorie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
