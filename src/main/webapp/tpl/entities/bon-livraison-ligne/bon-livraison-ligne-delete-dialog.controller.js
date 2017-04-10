(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonLivraisonLigneDeleteController',BonLivraisonLigneDeleteController);

    BonLivraisonLigneDeleteController.$inject = ['$uibModalInstance', 'entity', 'BonLivraisonLigne'];

    function BonLivraisonLigneDeleteController($uibModalInstance, entity, BonLivraisonLigne) {
        var vm = this;

        vm.bonLivraisonLigne = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BonLivraisonLigne.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
