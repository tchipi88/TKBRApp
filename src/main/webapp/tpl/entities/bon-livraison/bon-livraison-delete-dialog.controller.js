(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonLivraisonDeleteController',BonLivraisonDeleteController);

    BonLivraisonDeleteController.$inject = ['$uibModalInstance', 'entity', 'BonLivraison'];

    function BonLivraisonDeleteController($uibModalInstance, entity, BonLivraison) {
        var vm = this;

        vm.bonLivraison = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BonLivraison.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
