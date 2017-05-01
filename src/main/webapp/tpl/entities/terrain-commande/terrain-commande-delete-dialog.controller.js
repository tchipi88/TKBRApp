(function() {
    'use strict';

    angular
        .module('app')
        .controller('TerrainCommandeDeleteController',TerrainCommandeDeleteController);

    TerrainCommandeDeleteController.$inject = ['$uibModalInstance', 'entity', 'TerrainCommande'];

    function TerrainCommandeDeleteController($uibModalInstance, entity, TerrainCommande) {
        var vm = this;

        vm.terrainCommande = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TerrainCommande.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
