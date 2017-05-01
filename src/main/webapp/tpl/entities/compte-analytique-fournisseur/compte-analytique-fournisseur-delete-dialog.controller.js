(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueFournisseurDeleteController',CompteAnalytiqueFournisseurDeleteController);

    CompteAnalytiqueFournisseurDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompteAnalytiqueFournisseur'];

    function CompteAnalytiqueFournisseurDeleteController($uibModalInstance, entity, CompteAnalytiqueFournisseur) {
        var vm = this;

        vm.compteAnalytiqueFournisseur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompteAnalytiqueFournisseur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
