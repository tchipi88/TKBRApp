(function() {
    'use strict';

    angular
        .module('app')
        .controller('BonReceptionLigneDeleteController',BonReceptionLigneDeleteController);

    BonReceptionLigneDeleteController.$inject = ['$uibModalInstance', 'entity', 'BonReceptionLigne'];

    function BonReceptionLigneDeleteController($uibModalInstance, entity, BonReceptionLigne) {
        var vm = this;

        vm.bonReceptionLigne = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            BonReceptionLigne.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
