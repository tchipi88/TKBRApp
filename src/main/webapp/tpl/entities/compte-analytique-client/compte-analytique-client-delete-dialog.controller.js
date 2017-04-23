(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueClientDeleteController',CompteAnalytiqueClientDeleteController);

    CompteAnalytiqueClientDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompteAnalytiqueClient'];

    function CompteAnalytiqueClientDeleteController($uibModalInstance, entity, CompteAnalytiqueClient) {
        var vm = this;

        vm.compteAnalytiqueClient = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompteAnalytiqueClient.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
