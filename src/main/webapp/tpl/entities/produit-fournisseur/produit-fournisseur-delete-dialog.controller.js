(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .controller('ProduitFournisseurDeleteController',ProduitFournisseurDeleteController);

    ProduitFournisseurDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProduitFournisseur'];

    function ProduitFournisseurDeleteController($uibModalInstance, entity, ProduitFournisseur) {
        var vm = this;

        vm.produitFournisseur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProduitFournisseur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
