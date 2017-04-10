(function() {
    'use strict';

    angular
        .module('app')
        .controller('AchatDeleteController',AchatDeleteController);

    AchatDeleteController.$inject = ['$uibModalInstance', 'entity', 'Achat'];

    function AchatDeleteController($uibModalInstance, entity, Achat) {
        var vm = this;

        vm.achat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Achat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
