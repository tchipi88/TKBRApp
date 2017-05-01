(function() {
    'use strict';

    angular
        .module('app')
        .controller('LoyerDeleteController',LoyerDeleteController);

    LoyerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Loyer'];

    function LoyerDeleteController($uibModalInstance, entity, Loyer) {
        var vm = this;

        vm.loyer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Loyer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
